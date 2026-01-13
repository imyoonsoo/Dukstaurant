from django.urls import path, include
from rest_framework import routers
from footstep import views

router = routers.DefaultRouter()
router.register('footstep_drf', views.footstep_drf)


urlpatterns = [
    path('', include(router.urls)),
    path('footstep_post/', views.footstep_post),
   
]

 