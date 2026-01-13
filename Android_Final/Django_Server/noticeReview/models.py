from django.db import models
from order.models import Restaurant, Order
# from common.models import User

# from django.contri
# Create your models here.
class Notification(models.Model): #공지
    restaurant = models.ForeignKey(Restaurant, on_delete=models.CASCADE) 
    title = models.CharField(max_length=50)
    content = models.TextField()
    date = models.DateField()
    
class Review(models.Model): #리뷰
    restaurant = models.ForeignKey(Restaurant, on_delete=models.CASCADE) 
    order = models.ForeignKey(Order, on_delete=models.CASCADE)
    customer = models.EmailField(max_length=50)  #리뷰글 쓴 사람
    content = models.TextField()
