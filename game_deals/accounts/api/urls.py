from django.urls import path
from . import views

app_name = 'api'

urlpatterns = [
    path('login/', views.Login.as_view(), name='login'),
    path('register/', views.Register.as_view(), name='auth_register'),
    path('test/', views.TestGet.as_view(), name='test'), # for testing
]