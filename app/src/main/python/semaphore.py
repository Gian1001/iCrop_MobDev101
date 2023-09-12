import urllib.parse

apikey = "bdc45e3a5086805d089c6ba42acdb667"
sendername = 'TACDesk'
message = "I just sent my first message with Semaphore"
number = "09294423749"

def send_message():
    print('Sending Message...')
    params = (
        ('apikey', apikey),
        ('sendername', sendername),
        ('message', message),
        ('number', number)
    )
    path = 'https://semaphore.co/api/v4/messages?' + urllib.parse.urlencode(params)
    requests.post(path)
    print('Message Sent!')

# def main():
#     return "Hellow WOrd"
