package linear_regression

import breeze.linalg._
import breeze.stats.mean
import breeze.numerics._
import scala.util.Random
import linear_regression.StandartScaler

class LinearRegression(var lr: Double, nIter: Int, batchSize: Int) {
  private var W: DenseVector[Double] = DenseVector()
  private var b: Double = 0.0
  private var scaler = new StandartScaler()

  def MSE(predY: DenseVector[Double], trueY: DenseVector[Double]): Double = {
    // MSE err
    val res: Double = mean((predY -:- trueY).map(x => x * x))
    return res
  }

  def TrainTestSplit(X: DenseMatrix[Double], Y: DenseVector[Double], trainSize: Double):
                Tuple4[DenseMatrix[Double], DenseVector[Double], DenseMatrix[Double], DenseVector[Double]] = {
    // TODO
    val trainCount: Int = (X.rows * trainSize).toInt
    val ids: List[Int] = Random.shuffle(0 to (X.rows - 1)).toList
    val (trainRows, testRows) = ids.splitAt(trainCount)
    val trainX: DenseMatrix[Double] = X(trainRows, ::).toDenseMatrix
    val testX: DenseMatrix[Double] = X(testRows, ::).toDenseMatrix
    val trainY: DenseVector[Double] = Y(trainRows).toDenseVector
    val testY: DenseVector[Double] = Y(testRows).toDenseVector

    return (trainX, trainY, testX, testY)
  }

  def Fit(inpX: DenseMatrix[Double], Y: DenseVector[Double], verb: Boolean = false): Unit = {
    val X: DenseMatrix[Double] = this.scaler.FitTransform(inpX)
    val ids = 0 to (X.rows - 1)
    this.W = DenseVector.ones(X.cols)

    for (iter <- 1 to nIter) {
      val rows: List[Int] = Random.shuffle(ids).take(this.batchSize).toList
      val curX: DenseMatrix[Double] = X(rows, ::).toDenseMatrix
      val curY: DenseVector[Double] = Y(rows).toDenseVector
      var gradW = this.W
      var gradb = this.b

      for (i <- 0 to (batchSize - 1)) {
        val cur_X_val: DenseVector[Double] = curX(i, ::).t
        val cur_Y_val: Double = curY(i)
        gradW = (( -2.0 / batchSize) *:* cur_X_val * (cur_Y_val -:- cur_X_val.t * this.W -:- this.b)).toDenseVector
        gradb = (-2.0 / batchSize) *:* (cur_Y_val - cur_X_val.t * this.W - this.b)
        this.W -= this.lr *:* gradW
        this.b -= this.lr *:* gradb
      }

      this.lr /= 1.02

      if (verb) {
        val predY = this.Predict(curX)
        val loss: Double = this.MSE(predY, curY)
        println("Iter: " + iter.toString + " Train MSE: " + loss.toString)
      }
    }
  }

  def Predict(inpX: DenseMatrix[Double]): DenseVector[Double] = {
    val X: DenseMatrix[Double] = this.scaler.Transform(inpX)
    val y_pred: DenseVector[Double] = X * this.W +:+ this.b
    return y_pred
  }
}
