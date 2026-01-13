from rest_framework import serializers
from .models import footstep


class footstep_serializer(serializers.ModelSerializer):
    class Meta:
        model = footstep
        fields = "__all__"