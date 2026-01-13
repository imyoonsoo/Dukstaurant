from django.urls import path
from .views import TodayPreference, MyPreference, RecommendedMenuView

app_name = 'recommend'

urlpatterns = [
    path('todaypreference/', TodayPreference.as_view()),
    path('mypreference/', MyPreference.as_view()),
    path('recommendedmenuview/', RecommendedMenuView.as_view()),

]