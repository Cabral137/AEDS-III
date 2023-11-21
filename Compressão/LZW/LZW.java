import java.util.Scanner;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class LZW
{
    

    public static void compress ()
    {
        try 
		{
            Scanner sc = new Scanner(System.in);

            System.out.print("\nNome do arquivo: ");
            String path = sc.nextLine();

            RandomAccessFile ra = new RandomAccessFile("./" + path, "r");
            String linha = "";

            while(ra.getFilePointer() != ra.length())
            {
                linha = linha + ra.readLine();
            }

            Vector <String> dicionario = new Vector <String> ();

            for(int i = 0; i < 256; i++)
            {
                dicionario.add("" + (char) i);
            }

            String prefix = "";
            String comp   = "";

            for(int i = 0; i < linha.length(); i++)
            {
                prefix = prefix + linha.charAt(i);

                if(!dicionario.contains(prefix))
                {
                    dicionario.add(prefix);
                    comp   = comp + (dicionario.indexOf(prefix.substring(0, prefix.length()- 1)) + " ");
                    prefix = "" + linha.charAt(i);
                }
            }

            String [] verify = comp.split(" ");
            if(Integer.parseInt(verify[verify.length - 1]) > 255)
            {
                comp = comp + (int) linha.charAt(linha.length() - 1);
            }

            System.out.println(comp);

        }
        catch (Exception e) 
        {
            
        }
    }

    public static void decompress ()
    {
        try 
		{
            Scanner sc = new Scanner(System.in);

            // System.out.print("\nNome do arquivo: ");
            // String path = sc.nextLine();

            // RandomAccessFile ra = new RandomAccessFile("./" + path, "r");
            String linha = "65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 32 49 50 51 52 53 54 55 56 57 256 258 260 262 264 266 268 270 272 274 276 278 280 282 284 286 288 290 292 259 261 263 265 267 269 271 273 275 277 279 281 283 285 287 289 291 257 311 295 314 298 317 301 320 304 323 307 326 310 294 313 297 316 300 319 303 322 306 325 309 328 341 296 315 299 318 302 321 305 324 308 327 293 312 354 332 345 358 336 349 362 340 365 331 344 357 335 348 361 339 352 374 343 356 334 347 360 338 351 364 330 384 333 346 359 337 350 363 329 342 355 394 369 379 389 399 353 375 385 395 370 380 390 400 366 376 386 396 371 381 391 401 367 377 387 397 372 382 392 402 368 378 388 398 373 430 424 418 412 406 436 423 417 411 405 435 429 443 410 404 434 428 422 416 450 433 427 421 415 409 403 457 420 414 408 393 432 426 464 407 383 431 425 419 413 471 437 444 451 458 465 472 438 445 452 459 466 473 439 446 453 460 467 474 440 447 454 461 468 475 441 448 455 462 469 476 442 504 500 496 492 488 484 480 470 508 499 495 491 487 483 479 463 507 503 518 490 486 482 478 456 506 502 498 494 528 481 477 449 505 501 497 493 489 485 538 517 536 546 516 526 549 515 57";

            // while(ra.getFilePointer() != ra.length())
            // {
            //     linha = linha + ra.readLine();
            // }

            String [] nums = linha.split(" ");
            String decomp  = "";
            String aux     = "";

            Vector <String> dicionario = new Vector <String> ();

            for(int i = 0; i < 256; i++)
            {
                dicionario.add("" + (char) i);
            }

            for(int i = 0; i < nums.length; i++)
            {

                aux = aux + dicionario.get(Integer.parseInt(nums[i])).charAt(0);

                decomp = decomp + dicionario.get(Integer.parseInt(nums[i]));

                if(!dicionario.contains(aux))
                {
                    dicionario.add(aux);
                    aux = dicionario.get(Integer.parseInt(nums[i]));
                }

            }

            System.out.println(decomp);
  
		} 
		catch(Exception e) 
		{

		}  
    }
    

	public static void main(String[] args) 
	{
		
		// try 
		// {
        //     Scanner sc = new Scanner(System.in);

        //     // System.out.print("\nNome do arquivo: ");
        //     // String path = sc.nextLine();

        //     // RandomAccessFile ra = new RandomAccessFile("./" + path, "r");
        //     // String linha = "";

        //     // while(ra.getFilePointer() != ra.length())
        //     // {
        //     //     linha = linha + ra.readLine();
        //     // }

        //     Vector <String> dicionario = new Vector <String> ();

        //     for(int i = 0; i < 256; i++)
        //     {
        //         dicionario.add("" + (char) i);
        //     }

        //     String frase  = "WYS*WYGWYS*WYSWYSG";
        //     String prefix = "";
        //     String comp   = "";

        //     for(int i = 0; i < frase.length(); i++)
        //     {
        //         prefix = prefix + frase.charAt(i);

        //         if(!dicionario.contains(prefix))
        //         {
        //             dicionario.add(prefix);
        //             comp   = comp + (dicionario.indexOf(prefix.substring(0, prefix.length()- 1)) + " ");
        //             prefix = "" + frase.charAt(i);
        //         }
        //     }

        //     String [] verify = comp.split(" ");
        //     if(Integer.parseInt(verify[verify.length - 1]) > 255)
        //     {
        //         comp = comp + (int) frase.charAt(frase.length() - 1);
        //     }

        //     System.out.println(comp);

        //     // for(int i = 0; i < dicionario.size(); i++)
        //     // {
        //     //     System.out.println(dicionario.get(i) + " --- " + i);
        //     // }

        //     String [] nums = comp.split(" ");
        //     String decomp  = "";
        //     String aux     = "";

        //     dicionario = new Vector<String>();

        //     for(int i = 0; i < 256; i++)
        //     {
        //         dicionario.add("" + (char) i);
        //     }

        //     for(int i = 0; i < nums.length; i++)
        //     {

        //         aux = aux + dicionario.get(Integer.parseInt(nums[i])).charAt(0);

        //         decomp = decomp + dicionario.get(Integer.parseInt(nums[i]));

        //         if(!dicionario.contains(aux))
        //         {
        //             dicionario.add(aux);
        //             aux = dicionario.get(Integer.parseInt(nums[i]));
        //         }

        //     }

        //     System.out.println(decomp);
  
		// } 
		// catch(Exception e) 
		// {
		//     e.printStackTrace();
		// }

        compress();
		
	}
}
