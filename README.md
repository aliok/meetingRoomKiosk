# meetingRoomKiosk
A project to have cool meeting room kiosks.
Using tablets with motion and proximity sensors to wake up someone approaches and to show the information.

Sensors are Raspberry PI.
Tablets are Android.

Push notifications are sent to Android tablets triggered by Raspberry PIs. 

Well, not directly. Raspberry PIs ask a Spring application to send push notifications. That means, that Spring application is the who sends messages to Google Cloud Messaging.

See the project page: <http://www.aliok.com.tr/projects/2014-09-20-meeting-room-kiosk.html>
