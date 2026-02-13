import requests
import os
import json
from datetime import datetime
import psycopg2

api_key=os.getenv("STEAM_API_KEY")
#hi matt
# what up?
# nothing much, just working on the steam.py file
player_list = []

def load_steam_games(path):
    global STEAM_GAMES
    if not os.path.exists(path):
        raise FileNotFoundError("Can find" + path)
    with open(path, 'r', encoding='utf=8') as f:
        STEAM_GAMES = json.load(f)
    print("Loaded " + str(len(STEAM_GAMES)) + " games from " + path) 

class Game():
    name = ''
    steam_appid = ''
    is_free = ''
    short_description = ''
    header_image = ''
    capsule_image = ''
    developers = []
    publishers = []

class Player():
    steam_id = ''
    steam_name = ''
    profile_url = ''
    avatar = ''
    last_logoff = ''
    time_created = ''
    real_name = ''
    country_code = ''
    gameid = ''
    gameserverip = ''
    gameextrainfo = ''
    first_game = ''
    second_game = ''
    third_game = ''

def play_time_get(player):

    steamID = player.steam_id
    api_key="0AA30FC317E4A3ADCA63BD9F0C13A273"
    steam_owned_games_url = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=" + api_key + "&steamid="+ steamID + "&format=json"
    response = requests.get(steam_owned_games_url)
    report = response.json()
    raw_games = report['response']['games']
    first_playtime = 0
    second_playtime = 0
    third_playtime = 0
    first_appid = 0
    for game in raw_games:
        appid = int(game['appid'])
        playtime = int(game['playtime_forever'])
        if playtime > first_playtime:
            first_appid = appid
            first_playtime = playtime
        elif playtime > second_playtime:
            second_playtime = playtime
            second_appid = appid
        elif playtime > third_playtime:
            third_playtime = playtime
            third_appid = appid

    player.first_game = requests.get("https://store.steampowered.com/api/appdetails?appids=" + str(first_appid)).json()[str(first_appid)]['data']['name']
    player.second_game = requests.get("https://store.steampowered.com/api/appdetails?appids=" + str(second_appid)).json()[str(second_appid)]['data']['name']
    player.third_game = requests.get("https://store.steampowered.com/api/appdetails?appids=" + str(third_appid)).json()[str(third_appid)]['data']['name']

def get_game_info(game_id):
    steam_game_info_url = "https://store.steampowered.com/api/appdetails?appids=" + game_id.strip()
    response = requests.get(steam_game_info_url)
    report = response.json()
    game_info = report[game_id]
    cur_game = ''

    if game_info['success'] == True:
        game = game_info['data']
        cur_game = Game()
        cur_game.name = game['name']
        cur_game.steam_appid = game['steam_appid']
        cur_game.header_image = game['header_image']
        cur_game.is_free = game['is_free']
        for developer in game['developers']:
            cur_game.developers.append(developer)
        for publisher in game['publishers']:
            cur_game.publishers.append(publisher)
        cur_game.capsule_image = game['capsule_image']

    return cur_game

def steam_player_id(player_id):
    steamID = player_id
    api_key="0AA30FC317E4A3ADCA63BD9F0C13A273"
    steam_player_url = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + api_key + "&steamids=" + steamID
    response = requests.get(steam_player_url)
    report = response.json()

    for player in report['response']['players']:

        cur_player = Player()
        cur_player.steam_id = player['steamid']
        cur_player.steam_name = player['personaname']
        cur_player.profile_url = player['profileurl']
        cur_player.avatar = player['avatarfull']
        cur_player.last_logoff = str(datetime.fromtimestamp(int(player['lastlogoff'])))
        cur_player.time_created = str(datetime.fromtimestamp(int(player['timecreated'])))
        if player.get('realname'):
            cur_player.real_name = player['realname']
        if player.get('loccountrycode'):
            cur_player.country_code = player['loccountrycode']
        if player.get('gameid'):
            cur_player.gameid
        if player.get('gameid'):
            cur_player.gameid = player['gameid']
        if player.get('gameserverip'):
            cur_player.gameserverip = player.get('gameserverip')
        if player.get('gameextrainfo'):
            cur_player.gameextrainfo = player.get('gameextrainfo')
        
        play_time_get(cur_player)
       

        player_list.append(cur_player)

    connection = psycopg2.connect(database="gamerecall", user="gamerecalldba", password="test", host="thehomans.org", port=5432)    
    cursor = connection.cursor()
    cursor.execute("INSERT INTO players (steam_id, profile_url, avatar, last_logoff, time_created, first_game, second_game, third_game, steam_name, player_id) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,1)", 
                   (cur_player.steam_id, cur_player.profile_url,cur_player.avatar,cur_player.last_logoff,cur_player.time_created,cur_player.first_game,cur_player.second_game,cur_player.third_game,cur_player.steam_name))
    connection.commit()
    connection.close()
    print("sent!")
    return cur_player
    
    

if __name__ == "__main__":
    print("gaming")