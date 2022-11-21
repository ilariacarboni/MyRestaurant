/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  milar
 * Created: 14 nov 2022
 */
UPDATE menu
   SET nameDish = 'Frittata al tartufo'
 WHERE nameDish = 'Frittata';


INSERT INTO menu (
                     nameDish,
                     price,
                     course,
                     image
                 )
                 VALUES (
                     'Caffè',
                     '1.5',
                     'Bevande'
                 );
