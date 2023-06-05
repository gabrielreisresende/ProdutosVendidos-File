package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Product> productList = new ArrayList<>();

		System.out.println("Enter file path: ");
		String sourceFile = sc.nextLine();

		File source = new File(sourceFile);
		String sourceFolder = source.getParent();

		boolean success = new File(sourceFolder + "\\out").mkdir();

		String targetFile = sourceFolder + "\\out\\summary.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
			String product = br.readLine();

			while (product != null) {
				String[] products = product.split(",");
				String productName = products[0];
				double productPrice = Double.parseDouble(products[1]);
				int productQuantity = Integer.parseInt(products[2]);

				productList.add(new Product(productName, productPrice, productQuantity));
				product = br.readLine();
			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile))) {

				for (Product item : productList) {
					bw.write(item.getName() + "," + String.format("%.2f", item.getTotalPrice()));
					bw.newLine();
				}

				System.out.println(targetFile + " - CREATED!");

			} catch (IOException e) {
				System.out.println("Error writing file: " + e.getMessage());
			}

		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}

		sc.close();

	}

}
