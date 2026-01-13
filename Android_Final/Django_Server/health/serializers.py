from rest_framework import serializers
from .models import Myhealth_pre, Myhealth_post, Nutrition

class MyhealthSerializer_pre(serializers.ModelSerializer):
    class Meta:
        model = Myhealth_pre
        fields = "__all__"

class MyhealthSerializer_post(serializers.ModelSerializer):
    class Meta:  
        model = Myhealth_post
        fields = "__all__"

class NutritionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Nutrition
        fields = "__all__"

