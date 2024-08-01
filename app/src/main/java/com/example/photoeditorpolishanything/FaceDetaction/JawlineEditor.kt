package com.example.photoeditorpolishanything.FaceDetaction

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
//import org.opencv.core.Mat
//import org.opencv.core.MatOfPoint2f
//import org.opencv.core.Size
//import org.opencv.imgproc.Imgproc
//import org.opencv.android.Utils
//import org.opencv.imgproc.Subdiv2D

class JawlineEditor {

    /**
     * Adjusts the jawline of a detected face in the given bitmap.
     *
     * @param bitmap The original bitmap containing the face.
     * @param faceRect The bounding box of the detected face.
     * @param jawlineFactor A factor to control how much to modify the jawline. Positive values for enhancement, negative for reduction.
     * @return A new bitmap with the adjusted jawline.
     */
    fun adjustJawline(bitmap: Bitmap, faceRect: Rect, jawlineFactor: Float): Bitmap {
        val mat = Mat()
        Utils.bitmapToMat(bitmap, mat)

        // Create a mesh grid
        val points = createMeshGrid(faceRect)

        // Warp the mesh points to adjust the jawline
        val warpedPoints = warpMesh(points, jawlineFactor)

        // Apply the mesh warping to the image
        val resultMat = applyMeshWarping(mat, points, warpedPoints, faceRect)

        val resultBitmap = Bitmap.createBitmap(resultMat.cols(), resultMat.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(resultMat, resultBitmap)

        return resultBitmap
    }

    private fun createMeshGrid(faceRect: Rect): List<Point2f> {
        val points = mutableListOf<Point2f>()
        val stepX = faceRect.width() / 10
        val stepY = faceRect.height() / 10

        for (y in faceRect.top until faceRect.bottom step stepY) {
            for (x in faceRect.left until faceRect.right step stepX) {
                points.add(Point2f(x.toFloat(), y.toFloat()))
            }
        }
        return points
    }
//
//
    private fun warpMesh(points: List<Point2f>, jawlineFactor: Float): List<Point2f> {
        val warpedPoints = mutableListOf<Point2f>()
        for (point in points) {
            val newY = if (point.y > points.last().y - 20) { // Adjust this condition based on your jawline height
                point.y + jawlineFactor * 10 // Adjust this factor based on your needs
            } else {
                point.y
            }
            warpedPoints.add(Point2f(point.x, newY))
        }
        return warpedPoints
    }

    private fun applyMeshWarping(mat: Mat, srcPoints: List<Point2f>, dstPoints: List<Point2f>, faceRect: Rect): Mat {
        val resultMat = Mat(mat.size(), mat.type())
        val subdiv = Subdiv2D(faceRect.toRect())

        // Insert points into Subdiv2D
        srcPoints.forEach { subdiv.insert(it) }

        val triangleList = mutableListOf<MatOfPoint2f>()
        val trianglePoints = mutableListOf<Point2f>()

        // Get the triangle list
        val triangleMat = MatOfPoint2f()
        subdiv.getTriangleList(triangleMat)

        val triangleArray = triangleMat.toArray()
        for (i in triangleArray.indices step 3) {
            val tri = MatOfPoint2f()
            tri.fromArray(triangleArray[i], triangleArray[i + 1], triangleArray[i + 2])
            triangleList.add(tri)
        }

        // Apply warping for each triangle
        triangleList.forEach { triangle ->
            val srcTriangle = listOf(
                srcPoints[triangle[0].toInt()],
                srcPoints[triangle[1].toInt()],
                srcPoints[triangle[2].toInt()]
            )
            val dstTriangle = listOf(
                dstPoints[triangle[0].toInt()],
                dstPoints[triangle[1].toInt()],
                dstPoints[triangle[2].toInt()]
            )
            warpTriangle(mat, resultMat, srcTriangle, dstTriangle)
        }

        return resultMat
    }


    private fun warpTriangle(srcMat: Mat, dstMat: Mat, srcTriangle: List<Point2f>, dstTriangle: List<Point2f>) {
        val rect = Imgproc.boundingRect(MatOfPoint2f(*dstTriangle.toTypedArray()))

        val srcROI = Mat(srcMat, rect)
        val dstROI = Mat(dstMat, rect)

        val srcTriangleMat = MatOfPoint2f(*srcTriangle.map { Point2f(it.x - rect.x.toFloat(), it.y - rect.y.toFloat()) }.toTypedArray())
        val dstTriangleMat = MatOfPoint2f(*dstTriangle.map { Point2f(it.x - rect.x.toFloat(), it.y - rect.y.toFloat()) }.toTypedArray())

        val warpMat = Imgproc.getAffineTransform(srcTriangleMat, dstTriangleMat)
        Imgproc.warpAffine(srcROI, dstROI, warpMat, dstROI.size(), Imgproc.INTER_LINEAR, Imgproc.BORDER_REFLECT_101)
    }

    // Helper function to convert Rect to Mat
    private fun Rect.toRect(): org.opencv.core.Rect {
        return org.opencv.core.Rect(left, top, width(), height())
    }
}
