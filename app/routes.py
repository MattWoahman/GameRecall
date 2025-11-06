from app import app
from flask import render_template
from steam import steam_player_id,STEAM_GAMES
from app.forms import UserForm


@app.route('/', methods=['GET', 'POST'])
@app.route('/index', methods=['GET', 'POST'])
def index():
    form = UserForm()
    player = ''
    if form.validate_on_submit():
        steam_id = form.steamid.data
        player = steam_player_id(steam_id)
    return render_template('index.html', form=form, player=player)