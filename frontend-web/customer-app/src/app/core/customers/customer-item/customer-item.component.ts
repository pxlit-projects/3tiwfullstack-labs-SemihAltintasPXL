import { Component, Input, OnInit } from '@angular/core';
import { Customer } from '../../../shared/models/customer.model';
import { NgClass } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-customer-item',
  standalone: true,
  imports: [NgClass, RouterLink],
  templateUrl: './customer-item.component.html',
  styleUrl: './customer-item.component.css'
})
export class CustomerItemComponent {
  @Input() customer!: Customer;

  Customer = new Customer('John Doe', 'john@doe.com', 'Hasselt', 'elfdelinie 123', 'Belgium', 6);
  getDetails(): void{
    console.log(this.customer);
  }
}


