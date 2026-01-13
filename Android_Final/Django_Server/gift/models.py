from django.db import models

# Create your models here.
class Gift(models.Model):  #선물
    sender = models.EmailField(max_length=50)  #보낸 사람
    recipient =  models.EmailField(max_length=50)  #받은 사람
    giftMenu = models.CharField(max_length=30)  #보낸 메뉴
    used = models.BooleanField(default=False) #선물 사용 여부