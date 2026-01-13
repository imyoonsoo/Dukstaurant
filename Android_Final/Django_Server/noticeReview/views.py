from django.shortcuts import render
from .models import Notification, Review
from .serializers import NotificationSerializer, ReviewSerializer
from rest_framework import viewsets
from django.http import JsonResponse
import datetime

# Create your views here.
class Notification_drf(viewsets.ModelViewSet):
    queryset = Notification.objects.all().order_by("-date")
    serializer_class = NotificationSerializer

"""
class NotificationView(viewsets.ModelViewSet):
    current_time = datetime.datetime.now() #현재 시간
    queryset = Notification.objects.all() 
    queryset2 = Notification.objects.filter( 
        for obj in queryset:
            if obj.date > current_time:
                return True
    )



    serializer_class = NotificationSerializer
"""   
#def NotificationView(request):
    



class Review_drf(viewsets.ModelViewSet):
    queryset = Review.objects.all() 
    serializer_class = ReviewSerializer


def ReviewView(request):     
    if request.method == 'POST': 
        restaurant_get = request.POST.get('restaurant', '') 
        queryset = Review.objects.filter(restaurant=restaurant_get).order_by('-order')
        serializer_class = ReviewSerializer(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)

