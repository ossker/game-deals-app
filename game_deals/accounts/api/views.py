from rest_framework.exceptions import ValidationError
from rest_framework.permissions import AllowAny
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework import status, generics
from .serializers import LoginSerializer, RegisterSerializer
from ..models import User


class TestGet(APIView):
    def get(self, request):
        return Response({"message": "OK"})

class Login(APIView):

    # curl -X GET http://127.0.0.1:8000/api/example/ -H 'Authorization: Token <token>'

    def post(self, request):
        serializer = LoginSerializer(data=request.data, context={'request': request})
        try:
            serializer.is_valid(raise_exception=True)
            user = serializer.validated_data['user']
            token = serializer.validated_data['token']
            return Response({'token': token,
                             "user_id": user.id,
                             "username": user.username,
                             "email": user.email,
                             "first_name": user.first_name,
                             "last_name": user.last_name,
                             }, status=status.HTTP_200_OK)
        except ValidationError as e:
            error_message = str(e.detail['non_field_errors'][0])
            return Response({'error': error_message}, status=status.HTTP_400_BAD_REQUEST)
        except Exception:
            return Response({'error': 'Internal Error'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

class Register(generics.CreateAPIView):
    queryset = User.objects.all()
    permission_classes = (AllowAny,)
    serializer_class = RegisterSerializer