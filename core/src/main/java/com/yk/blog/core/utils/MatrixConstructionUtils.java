package com.yk.blog.core.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yikang
 * @date 2019/4/18
 */
public class MatrixConstructionUtils {

    //构建评分矩阵 行为文章列为用户 输入值input为三列 第一列为userId 第二列为blogId 第三列为评分
    public static int[][] scoreMatrix(List<String> userIds, List<Integer> blogIds, String[][] input) {
        String[][] tmp = new String[userIds.size() + 1][blogIds.size() + 1];
        int[][] result = new int[userIds.size()][blogIds.size()];
        for (int i = 1; i < tmp.length; i++) {
            tmp[i][0] = userIds.get(i - 1);
            tmp[0][i] = String.valueOf(blogIds.get(i - 1));
        }
        for (int i = 0; i < input.length; i++) {
            int x = 0, y = 0;
            for (int j = 0; j < input[0].length; j++) {
                for (int q = 1; q < tmp.length; q++) {
                    if (tmp[0][q].equals(input[i][j])) {
                        y = q;
                        break;
                    }
                }
                for (int q = 1; q < tmp[0].length; q++) {
                    if (tmp[q][0].equals(input[i][j])) {
                        x = q;
                        break;
                    }
                }
                if (x != 0 && y != 0) {
                    break;
                }
            }
            tmp[x][y] = input[2][i];
        }
        for (int i = 1; i < tmp.length; i++) {
            for (int j = 1; j < tmp[0].length; j++) {
                result[i - 1][j - 1] = Integer.valueOf(tmp[i][j]);
            }
        }
        return result;
    }

    //构建相似度矩阵
    public static int[][] similarMatrix(int[][] score, List<Integer> blogIds) {
        int[][] result = new int[score[0].length][score[0].length];

        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < score.length; i++) {
            for (int j = i + 1; j < score.length; j++) {
                for (int q = 1; q < score[0].length; q++) {
                    if (score[i][q] == score[j][q]) {
                        map.put(score[i] + ":" + score[j], map.getOrDefault(score[i] + ":" + score[j], 0) + 1);
                    }
                }
            }
        }
        for (Map.Entry entry : map.entrySet()
        ) {
            String blog1 = ((String) entry.getKey()).split(":")[0];
            String blog2 = ((String) entry.getKey()).split(":")[1];
            int x = 0, y = 0;
            for (int i = 0; i < blogIds.size(); i++) {
                if (x == 0 && (String.valueOf(blogIds.get(i)).equals(blog1) || String.valueOf(blogIds.get(i)).equals(blog2))) {
                    x = i;
                }
                if (x != 0 && (String.valueOf(blogIds.get(i)).equals(blog1) || String.valueOf(blogIds.get(i)).equals(blog2))) {
                    y = i;
                }
                if (x != 0 && y != 0) {
                    result[x][y] = (int) entry.getKey();
                    result[y][x] = (int) entry.getKey();
                    break;
                }
            }
        }
        return result;
    }

    //矩阵相乘，同为n*n阶矩阵
    public static int[][] multiplyMatrix(int[][] a, int[][] b) {
        if (a[0].length != b.length) {
            System.out.println("第一个矩阵的列不等于第二个矩阵的行！无法进行矩阵相乘运算！");
            return null;
        } else {
            int[][] c = new int[a.length][b[0].length];
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b[0].length; j++)
                    for (int t = 0; t < a[0].length; t++) {
                        c[i][j] += a[i][t] * b[t][j];
                    }
            }
            return c;
        }

    }


}
