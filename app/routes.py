from app import app
from flask import render_template
from steam import steam_player_id,STEAM_GAMES, get_game_info
from app.forms import UserForm,GameForm


@app.route('/', methods=['GET', 'POST'])
@app.route('/index', methods=['GET', 'POST'])
def index():
    return render_template('index.html')

@app.route('/player', methods=['GET', 'POST'])
def player():
    form = UserForm()
    player = ''
    if form.validate_on_submit():
        steam_id = form.steamid.data
        player = steam_player_id(steam_id)
    return render_template('players.html', player=player, form=form)

@app.route("/games", methods=['GET','POST'])
def games():
    form = GameForm()
    game = ""
    if form.validate_on_submit():
        game_id = form.gameid.data
        game = get_game_info(game_id)
        print(game.name)
    return render_template('games.html', game=game,form=form)