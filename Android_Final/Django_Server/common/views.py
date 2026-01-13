from django.shortcuts import render
from .models import User
from .serializers import UserSerializer
from rest_framework import viewsets
from  django.http import JsonResponse 

# Create your views here.

class UserView_drf(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer

 
def UserView(request):  #GET -> User 전체 불러오기    POST -> email해당하는 User 정보 보내기
    if request.method == 'GET':
        queryset = User.objects.all()
        serializer_class = UserSerializer(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)
    elif request.method == 'POST':
        get_email = request.POST.get('email', '') 
        queryset = User.objects.filter(email=get_email)
        serializer_class = UserSerializer(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)




    """ #dh
    elif request.method == 'POST':
        email = request.POST.get('email', '')
        password = request.POST.get('password', '')
        nickname = request.POST.get('nickname', '')
        restaurant_on = request.POST.get('restaurant_on', '')
        data = {'email':email, 'password':password, 'nickname': nickname, 'restaurant_on': restaurant_on}
        serializer_class = UserSerializer(data=data)
        if serializer_class.is_valid():
            serializer_class.save()
            return JsonResponse(status=201)
        return JsonResponse(status=400)
    """
