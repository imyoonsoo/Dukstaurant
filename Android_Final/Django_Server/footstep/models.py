from django.db import models
from common.models import User


app_name = 'footstep'

class footstep(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    date =  models.DateField()  
    step = models.IntegerField()