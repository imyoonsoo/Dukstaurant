from django.urls import path, include
from rest_framework import routers
from health import views

router = routers.DefaultRouter()
router.register('Myhealth_pre_drf', views.Myhealth_pre_drf)
router.register('Myhealth_post_drf', views.Myhealth_post_drf)
router.register('nutrition_drf', views.Nutrition_drf)


urlpatterns = [
    path('', include(router.urls)),
    path('nutrition/', views.NutritionView),
    path('Myhealth_pre/', views.MyHealthView_pre),
    path('Myhealth_post/', views.MyHealthView_post),
    path('health_post_drf/<int:id>/<str:date>/', views.Myhealth_post_drf.as_view({'put': 'update'}), name='update_health_post_drf'),
]

 