##!/usr/bin/env bash
#
#source /home/ubuntu/action/scripts/properties.sh
#
#PROJECT_NAME=mintspot
#REPOSITORY=/home/ubuntu/action
#PACKAGE=$REPOSITORY/build/libs/
#JAR_NAME=$(ls -tr $PACKAGE | grep 'SNAPSHOT.jar' | tail -n 1)
#JAR_PATH=$PACKAGE$JAR_NAME
#
#echo $JAR_NAME
#echo $JAR_PATH
#
#cd $REPOSITORY
#
#CURRENT_PID=$(pgrep -f $PROJECT_NAME)
#
#if [ -z $CURRENT_PID ]
#then
#  echo "> 종료할 애플리케이션이 없습니다"
#else
#  echo "> 실행 중인 애플리케이션 종료 $CURRENT_PID"
#  kill -15 $CURRENT_PID
#  sleep 5
#fi
#
#echo "> 배포 - $JAR_PATH"
#chmod +x $JAR_PATH
#
#sudo nohup java -jar $JAR_PATH --spring.profiles.active=develop > /home/ubuntu/log/nohup_log.out 2> /home/ubuntu/log/nohup_error.out &

#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/mint-spot
cd $REPOSITORY

APP_NAME=mint-spot
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z "$CURRENT_PID" ]
then
  echo "> 종료할 애플리케이션이 없습니다."
else
  echo "> kill -9 $CURRENT_PID"
  kill -9 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH에 실행권한 추가"
chmod +x $JAR_PATH

echo "> Deploy - $JAR_PATH "
nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &

echo nohup.out 2>$1 &