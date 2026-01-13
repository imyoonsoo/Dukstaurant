from django.urls import path, include
from rest_framework import routers
from gift import views

router = routers.DefaultRouter()
router.register('gift_drf', views.GiftView_drf)

urlpatterns = [
    path('', include(router.urls)),
    path('send/', views.SendView), 
    path('recipient/', views.RecipientView)
   
]