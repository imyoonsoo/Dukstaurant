from rest_framework import serializers
from .models import Notification, Review

class NotificationSerializer(serializers.ModelSerializer):  #공지 all
    class Meta:
        model = Notification
        fields = "__all__"

class ReviewSerializer(serializers.ModelSerializer): #리뷰 all
    class Meta:
        model = Review
        fields = "__all__"
