import breeze.linalg._
import standart_scaler.StandartScaler

class LinearRegression(lr: Double) {
  var W: DenseVector[Double] = DenseVector()

  def TrainValSplit(X: DenseMatrix[Double]): Seq[DenseMatrix[Double]] = {
    return Seq(X, X, X)
  }

  def CV(X: DenseMatrix[Double], Y: DenseVector[Double]): Double = {
    return 0.0
  }

  def Fit(X: DenseMatrix[Double], Y: DenseMatrix[Double], CVCount: Int = 3): Unit = {

  }

  def Predict(X: DenseMatrix[Double]): DenseVector[Double] = {
    return DenseVector.zeros(5)
  }
}