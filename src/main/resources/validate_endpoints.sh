echo Enter port number:
read PORT
echo $'\n+++++++++++++++++++++++++++++++++++++++++++++++++++\n'
echo $'CONTROLE 1: GET PARKING UUIDsE\n'
PARKINGS=$(curl -H Content-Type:application/json --no-progress-meter -X GET http://localhost:$PORT/parkings)
PARKING=$(echo $PARKINGS | sed -e 's/^\[\(.*\)\]$/\1/' | awk -F, '{print $1}' | sed -e 's/^"//' -e 's/"$//')
echo "Parkings $PARKINGS available..."
echo "Using parking $PARKING"
echo $'\n+++++++++++++++++++++++++++++++++++++++++++++++++++\n'
echo $'CONTROLE 2: START PARKING SESSION\n'
PAYLOAD=$(printf '{"parking": "%s", "licensePlate": "2-HRE-256"}' "$PARKING")
curl -d "$PAYLOAD" -H Content-Type:application/json --no-progress-meter -X POST http://localhost:$PORT/parking/start
echo $'\n+++++++++++++++++++++++++++++++++++++++++++++++++++\n'
echo $'\nSLEEP 4 seconds'
sleep 4s
echo $'CONTROLE 3: END PARKING SESSION\n'
curl -d '{"licensePlate": "2-HRE-256"}' -H Content-Type:application/json --no-progress-meter -X POST http://localhost:$PORT/parking/stop
echo $'\n+++++++++++++++++++++++++++++++++++++++++++++++++++\n'
echo $'CONTROLE 4: RETRIEVE RACE STATUS\n'
curl -H Content-Type:application/json --no-progress-meter -X GET http://localhost:$PORT/olympicgames/races/$RACEID/status
echo $'CONTROLE 8: SERVLET OUTPUT RACE $RACEID\n'
curl -H Content-Type:application/html --no-progress-meter -X GET http://localhost:$PORT/RaceResults?race=$RACEID
echo $'\n+++++++++++++++++++++++++++++++++++++++++++++++++++\n'
read -p "Press any key to continue..."
