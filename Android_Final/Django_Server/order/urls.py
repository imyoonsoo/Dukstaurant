from django.urls import path, include
from rest_framework import routers
from order import views

router = routers.DefaultRouter()
router.register('restaurant_drf', views.RestaurantView_drf)
router.register('menu_drf', views.MenuView_drf)
router.register('order_drf', views.OrderView_drf)

urlpatterns = [
    path('', include(router.urls)),
    path('menu/', views.MenuView),
    path('order_user/', views.OrderByUser),
    path('order_res/', views.OrderByRestaurant),
    path('menu_info/', views.MunuInfo),
    path('alarm/', views.OrderAlarm)
]