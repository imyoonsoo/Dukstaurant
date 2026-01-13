from django.shortcuts import render
from order.models import Order, Menu
from rest_framework import viewsets
from order.serializers import OrderSerializer
from  django.http import JsonResponse
from django.utils import timezone
from django.db.models import Count
from datetime import date
from rest_framework.views import APIView
from health.models import Myhealth_pre, Myhealth_post, Nutrition
from django.shortcuts import get_object_or_404
from django.views import View
from common.models import User
class TodayPreference(APIView):

    def get(self, request):
        today = date.today()
        menu_counts = Order.objects.filter(time__gte=today).values('menu').annotate(menu_count=Count('menu')).order_by('-menu_count')
        most_ordered_menu_list = [item['menu'] for item in menu_counts][:5]

        return JsonResponse({'most_ordered_menu_list': most_ordered_menu_list}, safe=False)


class MyPreference(APIView):

    def post(self, request):
        email_get = request.POST.get('email', '') 
        menu_counts = Order.objects.filter(customer=email_get).values('menu').annotate(menu_count=Count('menu')).order_by('-menu_count')
        most_ordered_menu_list = [item['menu'] for item in menu_counts][:5]

        return JsonResponse({'most_ordered_menu_list': most_ordered_menu_list}, safe=False)

# class Macro()
    
# # 1.클라이언트에게 user id을 받고, 모델은 Myhealth_pre, Myhealth_post를 받아온다.
# # 2. 1차조건 : Nutrition 의 kcal =< (먹어야 하는 kcal(Myhealth_pre) - 먹은 kcal(Myhealth_post))
# # 3. 먹어야 하는(Myhealth_pre) 탄수화물, 단백질, 지방 +-5 오차 범위, 없으면 +-5 씩 늘어남, 탄수화물, 단백질, 지방 다 충족해야 함.
# # 4. 있으면 리스트에 3개까지의 메뉴 담아서 리턴
# # 5. 해당하는 데이터가 없거나,  3개를 넘지 못하면  탄수화물, 단백질, 지방 순으로 하나의 오차범위에만 해당하는 메뉴를 골라서 리스트에 3개까지 저장


class RecommendedMenuView(View):
    def post(self, request):
        user_id = request.POST.get('user_id')
        user = get_object_or_404(User, id=user_id)
        

        myhealth_pre = Myhealth_pre.objects.filter(user=user).first()
        myhealth_post = Myhealth_post.objects.filter(user=user).first()

        if not myhealth_pre or not myhealth_post:
            return JsonResponse({"message": "User data not found."}, status=400)

        # 2. 먹어야 하는 kcal에서 먹은 kcal 뺌
        remaining_kcal = myhealth_pre.kcal - myhealth_post.totalKcal

        # 3. 탄수화물, 단백질, 지방 오차 범위를 정의
        error_range = 100

        # 4. 조건에 맞는 Nutrition을 필터링
        suitable_menus = Nutrition.objects.filter(
        kcal__lte=remaining_kcal,
        car__range=(
            (myhealth_pre.kcal * 0.125 - myhealth_post.total_car) - error_range,
            (myhealth_pre.kcal * 0.125 - myhealth_post.total_car) + error_range
        ),
        pro__range=(
            (myhealth_pre.kcal * 0.075 - myhealth_post.total_pro) - error_range,
            (myhealth_pre.kcal * 0.075 - myhealth_post.total_pro) + error_range
        ),
        fat__range=(
            (myhealth_pre.kcal * 0.022 - myhealth_post.total_fat) - error_range,
            (myhealth_pre.kcal * 0.022 - myhealth_post.total_fat) + error_range
         )
)[:3]


        if suitable_menus.exists():
            menus = [menu.menu for menu in suitable_menus]
        else:
            error_range = 200
            # 5. 조건에 맞는 메뉴가 없을 경우 오차 범위를 늘림. 
            suitable_menus = Nutrition.objects.filter(
                kcal__lte=remaining_kcal,
                car__range=(
                    (myhealth_pre.kcal * 0.125 - myhealth_post.total_car) - error_range,
                    (myhealth_pre.kcal * 0.125 - myhealth_post.total_car) + error_range
                ),
                pro__range=(
                    (myhealth_pre.kcal * 0.075 - myhealth_post.total_pro) - error_range,
                    (myhealth_pre.kcal * 0.075 - myhealth_post.total_pro) + error_range
                ),
                fat__range=(
                    (myhealth_pre.kcal * 0.022 - myhealth_post.total_fat) - error_range,
                    (myhealth_pre.kcal * 0.022 - myhealth_post.total_fat) + error_range
                )
            )[:3]


            menus = [menu.menu for menu in suitable_menus]

        return JsonResponse({"recommended_menus": menus}, status=200)