from django.shortcuts import render
from rest_framework import viewsets
from .models import footstep
from .serializers import footstep_serializer
from  django.http import JsonResponse


class footstep_drf(viewsets.ModelViewSet): 
    queryset = footstep.objects.all()
    serializer_class = footstep_serializer
     

def footstep_post(request):    #post로 요청이 들어왔을 때 footstep
    if request.method == 'POST': 
        user_get = request.POST.get('user', '') 
        date_get = request.POST.get('date', '')
        queryset = footstep.objects.filter(user=user_get, date=date_get)
        serializer_class = footstep_serializer(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)
   