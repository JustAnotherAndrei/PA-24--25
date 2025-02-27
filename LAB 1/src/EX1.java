public class EX1{
    public static void main(String[] args)
    {
        System.out.println("Hello world!");
        String[] languages = {"C","C++","C#","Python","Go","Rust","JavaScript","PHP","Swift","Java"};
        int n = (int) (Math.random() * 1_000_000);
        n *= 3;
        n += 0b10101;
        n += 0xFF;
        n *= 6;

        System.out.println(n);
        int cif_sum = 0;

        while(n>0)
        {
            cif_sum += n%10;
            n /= 10;
        }

        while(cif_sum>10)
        {
            int sum = 0;
            while(cif_sum != 0)
            {
                sum += cif_sum%10;
                cif_sum = cif_sum/10;
            }
            cif_sum = sum;
        }
        System.out.println("Willy-nilly, this semester I will learn " +languages[cif_sum]);}

}