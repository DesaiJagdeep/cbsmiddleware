import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategoryMaster, NewCategoryMaster } from '../category-master.model';

export type PartialUpdateCategoryMaster = Partial<ICategoryMaster> & Pick<ICategoryMaster, 'id'>;

export type EntityResponseType = HttpResponse<ICategoryMaster>;
export type EntityArrayResponseType = HttpResponse<ICategoryMaster[]>;

@Injectable({ providedIn: 'root' })
export class CategoryMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/category-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(categoryMaster: NewCategoryMaster): Observable<EntityResponseType> {
    return this.http.post<ICategoryMaster>(this.resourceUrl, categoryMaster, { observe: 'response' });
  }

  update(categoryMaster: ICategoryMaster): Observable<EntityResponseType> {
    return this.http.put<ICategoryMaster>(`${this.resourceUrl}/${this.getCategoryMasterIdentifier(categoryMaster)}`, categoryMaster, {
      observe: 'response',
    });
  }

  partialUpdate(categoryMaster: PartialUpdateCategoryMaster): Observable<EntityResponseType> {
    return this.http.patch<ICategoryMaster>(`${this.resourceUrl}/${this.getCategoryMasterIdentifier(categoryMaster)}`, categoryMaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoryMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoryMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategoryMasterIdentifier(categoryMaster: Pick<ICategoryMaster, 'id'>): number {
    return categoryMaster.id;
  }

  compareCategoryMaster(o1: Pick<ICategoryMaster, 'id'> | null, o2: Pick<ICategoryMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategoryMasterIdentifier(o1) === this.getCategoryMasterIdentifier(o2) : o1 === o2;
  }

  addCategoryMasterToCollectionIfMissing<Type extends Pick<ICategoryMaster, 'id'>>(
    categoryMasterCollection: Type[],
    ...categoryMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categoryMasters: Type[] = categoryMastersToCheck.filter(isPresent);
    if (categoryMasters.length > 0) {
      const categoryMasterCollectionIdentifiers = categoryMasterCollection.map(
        categoryMasterItem => this.getCategoryMasterIdentifier(categoryMasterItem)!
      );
      const categoryMastersToAdd = categoryMasters.filter(categoryMasterItem => {
        const categoryMasterIdentifier = this.getCategoryMasterIdentifier(categoryMasterItem);
        if (categoryMasterCollectionIdentifiers.includes(categoryMasterIdentifier)) {
          return false;
        }
        categoryMasterCollectionIdentifiers.push(categoryMasterIdentifier);
        return true;
      });
      return [...categoryMastersToAdd, ...categoryMasterCollection];
    }
    return categoryMasterCollection;
  }
}
