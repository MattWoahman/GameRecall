from flask import Flask
from config import Config
from steam import load_steam_games
path = 'steamgames.txt'

app = Flask(__name__)
app.config.from_object(Config)
load_steam_games(path)

from app import routes