package edu.northeastern.project_ar;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
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
    StorageReference storageReference;
    String model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storageReference = FirebaseStorage.getInstance().getReference("Amino Acids/" + "A.glb");
        model = storageReference.toString();
        Toast.makeText(AR_Activity3.this, model, Toast.LENGTH_LONG).show();

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arFragment.onCreate(savedInstanceState);
        // adding listener for detecting plane.
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();

            // adding model to the scene
            ModelRenderable.builder()
                    .setSource(this, RenderableSource.builder().setSource(
                                    this, Uri.parse(model), RenderableSource.SourceType.GLB)
                            .setScale(0.1f)
                            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                            .build())
                    .build()
                    .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable));
        });
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode node = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(node); // need to attach to parent
        transformableNode.setRenderable(modelRenderable);
        transformableNode.getLight();

        arFragment.getArSceneView().getScene().addChild(node); // adddding only parent node, so the child nodes will be added automatically
        transformableNode.select();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
