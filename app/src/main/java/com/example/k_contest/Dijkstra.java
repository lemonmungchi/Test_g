package com.example.k_contest;



public class  Dijkstra {
    private int n;                  // 도시의 총수
    private int[][] weight;         // 가중치 지도
    private String[] saveRoute;     // 이동 경로 저장
    private String[] vertex = {"창원", "진주", "통영", "사천","김해", "밀양", "거제", "양산", "의령",
    "함양", "창녕", "고성", "남해", "하동", "산청", "함안", "거창", "합천"};

    public Dijkstra(int n, int[][] matrix) {        // 변수 초기화
        super();
        this.n = n;
        this.weight = matrix;
        saveRoute = new String[n];
    }



    public int stringToInt(String s) {              // String to Int
        int x = 0;
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i].equals(s)) x = i;
        }
        return x;
    }

    public String[] algorithm(String a, String b) {
        boolean[] visited = new boolean[n];         // 방문한 지역 표시
        int distance[] = new int[n];                // 출발지로부터 거리 저장

        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        int x = stringToInt(a);
        distance[x] = 0;                             // 출발지에서 출발지 까지는 0 이니까
        visited[x] = true;
        saveRoute[x] = vertex[x];

        for (int i = 0; i < n; i++) {
            if (!visited[i] && weight[x][i] != 0) {     // 가중치 지도 복사
                distance[i] = weight[x][i];
                saveRoute[i] = vertex[x];
            }
        }

        // 도착 가능 여부 판단
        for(int i = 0; i < n-1; i++) {
            int minDistance = Integer.MAX_VALUE;
            int minVertex = -1;
            for(int j = 0; j < n; j++) {
                if(!visited[j] && distance[j]!=Integer.MAX_VALUE) {
                    if(distance[j] < minDistance) {
                        minDistance = distance[j];
                        minVertex = j;
                    }
                }
            }

            // 거리 계산
            visited[minVertex] = true;
            for(int j = 0; j < n; j++) {
                if(!visited[j] && weight[minVertex][j] != Integer.MAX_VALUE) {
                    if(distance[j] > distance[minVertex]+weight[minVertex][j]) {
                        distance[j] = distance[minVertex]+weight[minVertex][j];
                        saveRoute[j] = vertex[minVertex];
                    }
                }
            }
        }


        String route = "";
        String prev = b;
        while(true) {
            route += vertex[stringToInt(prev)];
            route += " ";
            prev = saveRoute[stringToInt(prev)];
            if(prev.equals(a)) {
                route+=a;
                break;
            }
        }
        String[] strArray = route.split(" ");


        return strArray;
    }
}
