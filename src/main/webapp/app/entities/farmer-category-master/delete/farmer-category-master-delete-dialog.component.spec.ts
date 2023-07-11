jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FarmerCategoryMasterService } from '../service/farmer-category-master.service';

import { FarmerCategoryMasterDeleteDialogComponent } from './farmer-category-master-delete-dialog.component';

describe('FarmerCategoryMaster Management Delete Component', () => {
  let comp: FarmerCategoryMasterDeleteDialogComponent;
  let fixture: ComponentFixture<FarmerCategoryMasterDeleteDialogComponent>;
  let service: FarmerCategoryMasterService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FarmerCategoryMasterDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(FarmerCategoryMasterDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FarmerCategoryMasterDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FarmerCategoryMasterService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
