from django.http import HttpResponse
from django.shortcuts import render
from django.contrib.auth import authenticate, login
from .forms import LoginForm, UserRegistrationForm
from django.contrib.auth.decorators import login_required

@login_required
def profile_dashboard(request):
    return render(request, 'accounts/profile_dashboard.html')

def user_login(request):
    if request.method == 'POST':
        form = LoginForm(request.POST)
        if form.is_valid():
            cd = form.cleaned_data
            user = authenticate(username=cd['username'],
                            password=cd['password'])
            if user is not None:
                if user.is_active:
                    login(request, user)
                    return HttpResponse('Uwierzytelnienie zakończyło się sukcesem.')
                else:
                    return HttpResponse('Konto jest zablokowane.')
            else:
                return HttpResponse('Nieprawidłowe dane uwierzytelniające.')
    else:
        form = LoginForm()
    return render(request, 'accounts/login.html', {'form': form})


def register(request):
     if request.method == 'POST':
         user_form = UserRegistrationForm(request.POST)
         if user_form.is_valid():
             new_user = user_form.save(commit=False)
             new_user.set_password(user_form.cleaned_data['password'])
             new_user.save()
             return render(request, 'accounts/profile_dashboard.html', {'new_user': new_user}) # TODO do zmiany
     else:
        user_form = UserRegistrationForm()
     return render(request, 'accounts/register.html', {'user_form': user_form})