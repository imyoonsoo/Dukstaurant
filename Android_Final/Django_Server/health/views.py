from django.shortcuts import render
from .models import Myhealth_pre,Myhealth_post, Nutrition
from .serializers import MyhealthSerializer_pre, MyhealthSerializer_post,NutritionSerializer
from rest_framework import viewsets
from  django.http import JsonResponse
from rest_framework.response import Response
from rest_framework import status


class Myhealth_pre_drf(viewsets.ModelViewSet): 
    queryset = Myhealth_pre.objects.all()
    serializer_class = MyhealthSerializer_pre


class Myhealth_post_drf(viewsets.ModelViewSet): 
    queryset = Myhealth_post.objects.all()
    serializer_class = MyhealthSerializer_post
        
class Nutrition_drf(viewsets.ModelViewSet): 
    queryset = Nutrition.objects.all()
    serializer_class = NutritionSerializer


def NutritionView(request):  #요청한 메뉴에 따라 영양정보 Response
    if request.method == 'POST': 
        menu_get = request.POST.get('menu', '') 
        queryset = Nutrition.objects.filter(menu=menu_get)
        serializer_class = NutritionSerializer(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)
    
def MyHealthView_pre(request):    # '먹기 전' 정보를 create
    if request.method == 'POST': 
        user_get = request.POST.get('user', '') 
        queryset = Myhealth_pre.objects.filter(user=user_get)
        serializer_class = MyhealthSerializer_pre(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)
   
  

def MyHealthView_post(request):    #'먹은 후' 정보를  create
    if request.method == 'POST': 
        user_get = request.POST.get('user', '') 
        date_get = request.POST.get('date', '')
        queryset = Myhealth_post.objects.filter(user=user_get, date=date_get)
        serializer_class = MyhealthSerializer_post(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)
   
#일단 user, date를 pk 값으로 만든다.
#이를 불러올 수 있도록 한다. 



# class Myhealth_post_drf(viewsets.ModelViewSet):
#     queryset = Myhealth_post.objects.all()
#     serializer_class = MyhealthSerializer_post

#     def create(self, request, *args, **kwargs):
#         user_get = request.data.get('user', '') 
#         date_get = request.data.get('date', '')
#         queryset = Myhealth_post.objects.filter(user=user_get, date=date_get)
#         serializer = self.get_serializer(queryset, many=True)
#         return Response(serializer.data)
