import breeze.linalg.{DenseMatrix, DenseVector, csvread, csvwrite}

import java.io.File
import scala.io.StdIn.readLine

import linear_regression.LinearRegression

object Main {
  def main(args: Array[String]): Unit = {
    val pathToTrainFile = readLine()
    val pathToTestFile = readLine()
    val pathToOutputFile = readLine()

//    val PathToFile: String = "/home/aslan/Documents/Github/MADE_ML_in_BigData/task_3/LinReg/data/dataset.csv"
    val TrainData = csvread(new File(pathToTrainFile), ',', skipLines = 1)
    val X: DenseMatrix[Double] = TrainData(::, 0 to -2)
    val Y: DenseVector[Double] = TrainData(::, -1)
    val LinReg = new LinearRegression(0.03, 10000, 20)
    val (trainX, trainY, valX, valY) = LinReg.TrainTestSplit(X, Y, 0.8)

    LinReg.Fit(trainX, trainY, false)

    var predY: DenseVector[Double] = LinReg.Predict(valX)
    var predMSE = LinReg.MSE(predY, valY)
    println("Val MSE: " + predMSE.toString)

    val TestData = csvread(new File(pathToTestFile), ',', skipLines = 1)
    val testX: DenseMatrix[Double] = TestData(::, 0 to -2)
    val testY: DenseVector[Double] = TestData(::, -1)

    predY = LinReg.Predict(testX)
    predMSE = LinReg.MSE(predY, testY)
    println("Test MSE: " + predMSE.toString)
    println("Prediction written to " + pathToOutputFile)
    csvwrite(new File(pathToOutputFile), predY.asDenseMatrix.t)
  }
}
