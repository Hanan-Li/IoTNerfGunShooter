import threading
import socket
import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)
GPIO.setup(11, GPIO.OUT)
servo1 = GPIO.PWM(11,50)
running = True

def InitServo():
    servo1.start(0)

def TurnToDeg(degrees):
    changeScale = degrees/18
    servoDuty = 2.2 + changeScale
    servo1.ChangeDutyCycle(servoDuty)
    time.sleep(0.3)
    servo1.ChangeDutyCycle(0)
    time.sleep(0.3)

def ShootDart(deg):
    print("shooting")
    TurnToDeg(0)
    TurnToDeg(deg)
    TurnToDeg(0)

def Cleanup():
    servo1.stop()
    GPIO.cleanup()
    print("DONE")


def StartBtServer():
    global running
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    port = 5000
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    host = ''
    sock.bind((host, port))
    sock.listen(5)

    sock.settimeout(1)
    print("starting server")
    while running:
        time.sleep(0.1)
        try:
            clientsocket, addr = sock.accept()
        except socket.timeout:
            continue
        print("Connection from", addr[0])

        message_pieces = []
        while running:
            try:
                chunk = clientsocket.recv(4096)
            except socket.timeout:
                continue
            if not chunk:
                break
            message_pieces.append(chunk)
        clientsocket.close()

            # Decode list-of-byte-strings to UTF8 and parse JSON data
        message_bytes = b''.join(message_pieces)
        message_str = message_bytes.decode("utf-8")
        print(message_str)
        if message_str[0:5] == "shoot":
            deg = int(message_str[5:7])
            ShootDart(deg);
        elif message_str == "stop":
            running = False
    sock.close()
    Cleanup()

if __name__ == '__main__':
    InitServo()
    StartBtServer()
