from django.urls import path, include
from rest_framework import routers
from noticeReview import views

router = routers.DefaultRouter()
router.register('notification_drf', views.Notification_drf) #공지 drf
router.register('review_drf', views.Review_drf) #공지 drf


urlpatterns = [
    path('', include(router.urls)),
    path('review/', views.ReviewView),
    #path('notification/', views.NotificationView),
]
 