from django.shortcuts import render
from .models import Restaurant, Menu, Order
from .serializers import RestaurantSerializer, MenuSerializer, OrderSerializer
from rest_framework import viewsets
from  django.http import JsonResponse

# Create your views here.
class RestaurantView_drf(viewsets.ModelViewSet):
    queryset = Restaurant.objects.all()
    serializer_class = RestaurantSerializer

class MenuView_drf(viewsets.ModelViewSet):
    queryset = Menu.objects.all() 
    serializer_class = MenuSerializer

def MenuView(request):  #식당별 메뉴
    if request.method == 'POST': 
        restaurant_get = request.POST.get('restaurant', '') 
        queryset = Menu.objects.filter(restaurant=restaurant_get)
        serializer_class = MenuSerializer(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)
    
def MunuInfo(request):  #메뉴 이름에 따라 메뉴 정보 넘겨주는 url
    if request.method == "POST":
        get_menu =  request.POST.get('menu_name', '') 
        queryset = Menu.objects.filter(menu_name=get_menu)
        serializer_class = MenuSerializer(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)
        
class OrderView_drf(viewsets.ModelViewSet):
    queryset = Order.objects.all()
    serializer_class = OrderSerializer

def OrderByUser(request):   #주문한 email에 따라 주문 내역 보여줌 #사용자별 주문내역
    if request.method == 'POST': 
        email_get = request.POST.get('email', '') 
        queryset = Order.objects.filter(customer=email_get).order_by('-time')
        serializer_class = OrderSerializer(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)

def OrderByRestaurant(request):  #조회한 가게 id에 따라 주문 내역 보여줌 #가게 측 주문내역
    if request.method == 'POST':
        res_get = request.POST.get('restaurant', '') 
        queryset = Order.objects.filter(restaurant=res_get, done=False)  #완료되지 않은 주문만 조회
        serializer_class = OrderSerializer(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)
    
def OrderAlarm(requset):
    if requset.method == 'POST':
        id_get = requset.POST.get('id', '')
        queryset = Order.objects.filter(id=id_get)
        serializer_class = OrderSerializer(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)
    