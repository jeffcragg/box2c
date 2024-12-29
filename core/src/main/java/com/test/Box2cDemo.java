package com.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.box2d.Box2d;
import com.badlogic.gdx.box2d.enums.b2BodyType;
import com.badlogic.gdx.box2d.structs.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


import static com.badlogic.gdx.box2d.Box2d.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Box2cDemo extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;



    @Override
    public void create() {
        setupWorld();
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");

    }

    private static void setupWorld() {
        Box2d.initialize();

        // Ported from https://github.com/erincatto/box2d/blob/f377034920c42a26cd498c0a0b1b2e9f2b064989/test/test_world.c

        // Construct a world object, which will hold and simulate the rigid bodies.
        b2WorldDef worldDef = b2DefaultWorldDef();
        worldDef.gravity().x(0.0f);
        worldDef.gravity().y(-10.0f);

        b2WorldId worldId = b2CreateWorld(worldDef.asPointer());

        // Define the ground body.
        b2BodyDef groundBodyDef = b2DefaultBodyDef();
        groundBodyDef.position().x(0.0f);
        groundBodyDef.position().y(-10.0f);

        // Call the body factory which allocates memory for the ground body
        // from a pool and creates the ground box shape (also from a pool).
        // The body is also added to the world.
        b2BodyId groundId = b2CreateBody(worldId, groundBodyDef.asPointer());

        // Define the ground box shape. The extents are the half-widths of the box.
        b2Polygon groundBox = b2MakeBox(50.0f, 10.0f);

        // Add the box shape to the ground body.
        b2ShapeDef groundShapeDef = b2DefaultShapeDef();
        b2CreatePolygonShape(groundId, groundShapeDef.asPointer(), groundBox.asPointer());

        // Define the dynamic body. We set its position and call the body factory.
        b2BodyDef bodyDef = b2DefaultBodyDef();
        bodyDef.type(b2BodyType.b2_dynamicBody);
        bodyDef.position().x(0.0f);
        bodyDef.position().y(4.0f);

        b2BodyId bodyId = b2CreateBody(worldId, bodyDef.asPointer());

        // Define another box shape for our dynamic body.
        b2Polygon dynamicBox = b2MakeBox(1.0f, 1.0f);

        // Define the dynamic body shape
        b2ShapeDef shapeDef = b2DefaultShapeDef();

        // Set the box density to be non-zero, so it will be dynamic.
        shapeDef.density(1.0f);

        // Override the default friction.
        shapeDef.friction(0.3f);

        // Add the shape to the body.
        b2CreatePolygonShape(bodyId, shapeDef.asPointer(), dynamicBox.asPointer());
    }

    int renderCount =0;

    @Override
    public void render() {


        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        System.out.println("renderCount :"+renderCount++);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
