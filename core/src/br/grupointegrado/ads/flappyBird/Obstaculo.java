package br.grupointegrado.ads.flappyBird;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Laydi on 26/10/2015.
 */

public class Obstaculo {

    private World mundo;
    private OrthographicCamera camera;
    private Body corpoCima, corpoBaixo;
    private float posX;
    private float posYCima, posYBaixo;
    private float altura, largura;
    private boolean passou;
    private Obstaculo ultimoObstaculo; // ultima antes do atual
    private final Texture texturaCima;
    private final Texture textureBaixo;

    public Obstaculo(World mundo, OrthographicCamera camera, Obstaculo ultimoObstaculo,
                     Texture texturaCima, Texture textureBaixo){
        this.mundo = mundo;
        this.camera = camera;
        this.ultimoObstaculo = ultimoObstaculo;
        this.texturaCima = texturaCima;
        this.textureBaixo = textureBaixo;

        initPosicao();
        initCorpoCima();
        initCorpoBaixo();


    }

    private void initCorpoBaixo() {
        corpoBaixo = Util.criarCorpo(mundo, BodyDef.BodyType.StaticBody, posX, posYBaixo);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(largura / 2, altura / 2);

        Util.criarForma(corpoBaixo, shape, "OBSTACULO_BAIXO");

        shape.dispose();
    }

    private void initCorpoCima() {
        corpoCima = Util.criarCorpo(mundo, BodyDef.BodyType.StaticBody, posX, posYCima);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(largura / 2, altura / 2);

        Util.criarForma(corpoCima, shape, "OBSTACULO_CIMA");

        shape.dispose();
    }

    private void initPosicao() {
        largura = 40 / Util.PIXEL_METRO;
        altura = camera.viewportHeight / Util.PIXEL_METRO;

        float xInicial = largura + (camera.viewportWidth / 2 / Util.PIXEL_METRO);
        if (ultimoObstaculo != null)
            xInicial = ultimoObstaculo.getPosX();

        posX = xInicial + 4; // 4 é o espaço entre os obstaculos

        float parcela = (altura - Util.ALTURA_CHAO) /6; // tamanho da tela dividido por 6 para encontrar a posisao Y do obstaculo

        int multiplicador = MathUtils.random(1,3); //numero aleatorio entre 1 e 3

        posYBaixo = Util.ALTURA_CHAO + (parcela * multiplicador) - (altura / 2);
        posYCima = posYBaixo + altura + 2f; // 3f espaço entre os canos

    }

    public float getPosX(){
        return this.posX;
    }

    public void remover(){
        mundo.destroyBody(corpoCima);
        mundo.destroyBody(corpoBaixo);
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getLargura() {
        return largura;
    }

    public void setLargura(float largura) {
        this.largura = largura;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public boolean isPassou() {
        return passou;
    }

    public void setPassou(boolean passou) {
        this.passou = passou;
    }

    public void renderizar(SpriteBatch pincel){
        float x = (corpoCima.getPosition().x - largura / 2) * Util.PIXEL_METRO;
        float y = (corpoCima.getPosition().y - altura / 2)  * Util.PIXEL_METRO;
        pincel.draw(texturaCima, x, y, largura * Util.PIXEL_METRO, altura * Util.PIXEL_METRO);

        x = (corpoBaixo.getPosition().x - largura / 2) * Util.PIXEL_METRO;
        y = (corpoBaixo.getPosition().y - altura / 2) * Util.PIXEL_METRO;
        pincel.draw(textureBaixo, x, y, largura * Util.PIXEL_METRO, altura * Util.PIXEL_METRO);

    }

}
