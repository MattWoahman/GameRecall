from flask_wtf import FlaskForm
from wtforms import StringField, PasswordField, BooleanField, SubmitField
from wtforms.validators import DataRequired

class UserForm(FlaskForm):
    steamid = StringField('Steam ID', validators=[DataRequired()])
    submit = SubmitField('Check User')

class GameForm(FlaskForm):
    gameid = StringField('Game ID', validators=[DataRequired()])
    submit = SubmitField("Check Game")