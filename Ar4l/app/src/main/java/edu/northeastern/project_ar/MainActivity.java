package edu.northeastern.project_ar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {
    private ArFragment arFragment;
    private ModelRenderable modelRenderable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arFragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_me);
        setUpModel();
    }
    private void setUpModel(){
        //adding model to Scene
        ModelRenderable.builder()
                .setSource(this,R.raw.male)
                .build()
                .thenAccept(renderable1 -> modelRenderable=renderable1)
                .exceptionally(throwable -> {
                    Toast.makeText(MainActivity.this," model can't be loaded",Toast.LENGTH_SHORT).show();
                    return null;
                });
    }

    private void setUpPlane(){
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                Anchor anchor=hitResult.createAnchor();
                AnchorNode anchorNode= new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());
                creatModel(anchorNode);
            }
        });
    }

    private void creatModel(AnchorNode anchorNode){
        TransformableNode node= new TransformableNode(arFragment.getTransformationSystem());
        node.setParent(anchorNode);
        node.setRenderable(modelRenderable);
        node.select();
    }


}