echo Enter port number:
read PORT
echo $'\n+++++++++++++++++++++++++++++++++++++++++++++++++++\n'
PARKING="10d10f17-29c3-42c9-9ef8-744ba43cc52b"
echo $'CONTROLE 1: START PARKING SESSION\n'
PAYLOAD=$(printf '{"parking": "%s", "licensePlate": "2-HRE-256"}' "$PARKING")
curl -d "$PAYLOAD" -H Content-Type:application/json --no-progress-meter -X POST http://localhost:$PORT/parking/start
echo $'\n+++++++++++++++++++++++++++++++++++++++++++++++++++\n'
echo $'CONTROLE 2: RETRIEVE PARKING SESSIONS\n'
curl -H Content-Type:application/json --no-progress-meter -X GET http://localhost:$PORT/ParkingSessions?size=5&page=0&licensePlate=2-HRE-256
echo $'\n+++++++++++++++++++++++++++++++++++++++++++++++++++\n'
echo $'\nSLEEP 4 seconds'
sleep 4s
echo $'CONTROLE 3: END PARKING SESSION\n'
curl -d '{"licensePlate": "2-HRE-256"}' -H Content-Type:application/json --no-progress-meter -X POST http://localhost:$PORT/parking/stop
echo $'\n+++++++++++++++++++++++++++++++++++++++++++++++++++\n'
echo $'CONTROLE 4: RETRIEVE PARKING SESSIONS\n'
curl -H Content-Type:application/json --no-progress-meter -X GET http://localhost:$PORT/ParkingSessions?size=5&page=0&licensePlate=2-HRE-256
echo $'\n+++++++++++++++++++++++++++++++++++++++++++++++++++\n'
read -p "Press any key to continue..."
