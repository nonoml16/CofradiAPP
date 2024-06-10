import { Component, OnInit } from '@angular/core';
import { UserItemList } from '../../models/user-item-list';
import { UserService } from '../../services/user.service';

const FILTER_PAG_REGEX = /[^0-9]/g;

@Component({
  selector: 'app-users-list-page',
  templateUrl: './users-list-page.component.html',
  styleUrl: './users-list-page.component.css',
})
export class UsersListPageComponent implements OnInit {
  userItems: UserItemList[] = [];
  page = 1;
  pageSize = 11;
  collectionSize = 0;

  constructor(private userService: UserService) {}
  ngOnInit(): void {
    this.fetchBandas();
  }

  fetchBandas() {
    this.userService.getUserList().subscribe((items) => {
      this.userItems = items;
      this.collectionSize = items.length;
    });
  }
  get paginatedItems(): UserItemList[] {
    return this.userItems.slice(
      (this.page - 1) * this.pageSize,
      this.page * this.pageSize
    );
  }

  selectPage(page: string) {
    this.page = parseInt(page, 10) || 1;
  }

  formatInput(input: Event) {
    const target = input.target as HTMLInputElement;
    target.value = target.value.replace(FILTER_PAG_REGEX, '');
  }
}
