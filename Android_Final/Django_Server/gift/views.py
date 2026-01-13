from django.shortcuts import render
from .models import Gift
from .serializers import GiftSerializer
from rest_framework import viewsets
from  django.http import JsonResponse

# Create your views here.
class GiftView_drf(viewsets.ModelViewSet):
    queryset = Gift.objects.all() 
    serializer_class = GiftSerializer

def SendView(request):
    if request.method == 'POST': 
        sender_get = request.POST.get('sender', '') 
        queryset = Gift.objects.filter(sender=sender_get).order_by('-id')
        serializer_class = GiftSerializer(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)
    
def RecipientView(request):
    if request.method == 'POST': 
        recipient_get = request.POST.get('recipient', '') 
        queryset = Gift.objects.filter(recipient=recipient_get, used=False)
        serializer_class = GiftSerializer(queryset, many=True)
        return JsonResponse(serializer_class.data, safe=False)