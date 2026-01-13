from django.db import models
from common.models import User


class Myhealth_pre(models.Model):   #먹기 전 db
    user =  models.ForeignKey(User, on_delete=models.CASCADE)
    kcal = models.FloatField()
    # car = models.FloatField()
    # pro = models.FloatField()
    # fat = models.FloatField()    

class Myhealth_post(models.Model):   #먹은 후 db
    user =  models.ForeignKey(User, on_delete=models.CASCADE)
    date =  models.DateField()       #어느 날짜에 해당하는 Myhealth DB인지
    totalKcal = models.FloatField()
    total_car = models.FloatField()
    total_pro = models.FloatField()
    total_fat = models.FloatField()

class Nutrition(models.Model):  #메뉴의 영양정보
    menu = models.CharField(max_length=30)
    kcal = models.FloatField()
    car = models.FloatField()
    pro = models.FloatField()
    fat = models.FloatField()


    