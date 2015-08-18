package br.grupointegrado.SpaceInvaders;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


/**
 * Created by Joao Paulo on 03/08/2015.
 */
public class TelaJogo extends TelaBase{


    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage palco;
    private BitmapFont fonte;
    private Label lbpontuacao;
    private Image jogador;
    private Texture texturaJogador;
    private Texture texturaJogadorDireita;
    private Texture texturaJogadorEsquerda;
    private boolean indoDireita;
    private boolean indoEsquerda;
    private boolean indoCima;
    private boolean indoBaixo;

    /**
     * construtor padrão da tela de jogo
     * @param game referencia para a classe principal
     */
    public TelaJogo(MainGame game) {
        super(game);
    }

    /**
     * chamado quando a tela é exibida
     */
    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        palco = new Stage(new FillViewport(camera.viewportWidth, camera.viewportHeight, camera));

        initFonte();
        initInformacoes();
        initJogador();

    }

    private void initJogador() {
        texturaJogador = new Texture("sprites/player.png");
        texturaJogadorDireita = new Texture("sprites/player-right.png");
        texturaJogadorEsquerda = new Texture("sprites/player-left.png");

        jogador = new com.badlogic.gdx.scenes.scene2d.ui.Image(texturaJogador);
        float x = camera.viewportWidth / 2 - jogador.getWidth() / 2;
        float y = 10;
        jogador.setPosition(x, y);
        palco.addActor(jogador);
    }

    private void initInformacoes() {
        Label.LabelStyle lbEstilo = new Label.LabelStyle();
        lbEstilo.font = fonte;
        lbEstilo.fontColor = com.badlogic.gdx.graphics.Color.WHITE;


        lbpontuacao = new Label("0 pontos", lbEstilo);
        palco.addActor(lbpontuacao);
    }

    private void initFonte() {
        fonte = new BitmapFont();
    }

    /**
     * chamado a todo quadro de atualização do jogo(FPS)
     * @param delta tempo entre um quadro e outro(em segundo)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.05f, .05f, .15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        lbpontuacao.setPosition(10, camera.viewportHeight - 20);
        capturaTeclas();
        atualizarJogador(delta);

        palco.act(delta);
        palco.draw();

    }

    private void atualizarJogador(float delta) {
        float velocidade = 200; //Velocidade de movimento do jogador
        if (indoDireita) {
            if (jogador.getX() < camera.viewportWidth - jogador.getWidth()) {
                float x = jogador.getX() + velocidade * delta;
                float y = jogador.getY();
                jogador.setPosition(x, y);

            }
        }
        if (indoEsquerda) {
            if (jogador.getX() > 0) {
                float x = jogador.getX() - velocidade * delta;
                float y = jogador.getY();
                jogador.setPosition(x, y);

            }
        }
        if (indoCima) {
            float x = jogador.getX();
            float y = jogador.getY() + velocidade * delta;
            jogador.setPosition(x, y);

        }

        if (indoBaixo) {
            float x = jogador.getX();
            float y = jogador.getY() - velocidade * delta;
            jogador.setPosition(x, y);


        }
        if (indoDireita) {
            //trocar imagem direita
            jogador.setDrawable(new SpriteDrawable(new Sprite(texturaJogadorDireita)));
        }else if (indoEsquerda) {
            //trocar imagem esqueda
            jogador.setDrawable(new SpriteDrawable(new Sprite(texturaJogadorEsquerda)));
        }else {
            //trocar imagem cena
            jogador.setDrawable(new SpriteDrawable(new Sprite(texturaJogador)));
        }

    }

    private void capturaTeclas() {
        indoDireita = false;
        indoEsquerda = false;
        indoCima = false;
        indoBaixo = false;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            indoEsquerda = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            indoDireita = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            indoCima = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            indoBaixo = true;
        }
    }

    /**
     * chamado sempre que ha uma alteração no tamanho da tela
     * @param width muda valor de largura da tela
     * @param height muda valor de altura da tela
     */
    @Override
    public void resize(int width, int height) {

        camera.setToOrtho(false, width, height);
        camera.update();
    }

    /**
     * chamado sempre que o jogo for minimizado
     */
    @Override
    public void pause() {

    }

    /**
     * resume sempre que o jogo voltar para o primeiro plano
     */
    @Override
    public void resume() {

    }

    /**
     * chamado quando a tela for destruida
     */
    @Override
    public void dispose() {

        batch.dispose();
        palco.dispose();
        fonte.dispose();
        texturaJogador.dispose();
        texturaJogadorDireita.dispose();
        texturaJogadorEsquerda.dispose();
    }
}
