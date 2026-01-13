from django.db import models
  
# Create your models here.
class Restaurant(models.Model):  #식당
    restaurant_name = models.CharField(max_length=30)
    def __str__(self):
        return self.restaurant_name

class Menu(models.Model):  #메뉴
    restaurant = models.ForeignKey(Restaurant, on_delete=models.CASCADE)  #Restaurant 삭제시 Menu도 삭제
    menu_name = models.CharField(max_length=30)
    price = models.IntegerField()  
    def __str__(self):
        return self.menu_name

class Order(models.Model): #주문
    menu = models.CharField(max_length=30) #models.ForeignKey(Menu, on_delete=models.CASCADE)
    restaurant = models.ForeignKey(Restaurant, on_delete=models.CASCADE)
    customer = models.EmailField(max_length=30)
    done = models.BooleanField(default=False)
    time = models.DateField(auto_now_add=True)  #매번 자동으로 현재시각 설정