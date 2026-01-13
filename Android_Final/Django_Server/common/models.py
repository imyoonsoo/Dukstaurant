from django.db import models

# Create your models here.
class User(models.Model):  
    email = models.EmailField(max_length=50)
    password = models.CharField(max_length=15)
    nickname = models.CharField(max_length=15)
    restaurant_on = models.BooleanField(default=False)
    #image = models.ImageField(null=True, blank=True)
    def __str__(self):
        return self.email 