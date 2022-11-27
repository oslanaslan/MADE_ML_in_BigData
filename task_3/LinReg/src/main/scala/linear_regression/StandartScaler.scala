package linear_regression

import breeze.linalg._
import breeze.stats.{mean, stddev}

class StandartScaler {
  private var _mean: DenseVector[Double] = DenseVector()
  private var _std: DenseVector[Double] = DenseVector()

  def Fit(X: DenseMatrix[Double]): Unit = {
    this._mean = mean(X(::, *)).t
    this._std = stddev(X(::, *)).t
  }

  def Transform(X: DenseMatrix[Double]): DenseMatrix[Double] = {
    var res = X(*, ::) - this._mean
    res = res(*, ::) / this._std
    return res
  }

  def FitTransform(X: DenseMatrix[Double]): DenseMatrix[Double] = {
    Fit(X)
    val res: DenseMatrix[Double] = Transform(X)
    return res
  }
}
