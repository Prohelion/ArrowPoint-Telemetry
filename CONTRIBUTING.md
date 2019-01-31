## Contributing

First off, thank you for considering contributing to the Prohelion ArrowPoint Telemetry tools. Prohelion is made up of former 
and current electric vehicle racers from around the world and we have always enjoyed the support of the community in the events 
we have been involved with.  By open sourcing our software we are hoping to support other teams based on our
learnings and experience, by contributing to the code we apprecite you helping as well.

### Where do I go from here?

If you've noticed a bug or have a question [search the issue tracker][] to see if
someone else in the community has already created a ticket. If not, go ahead and
[make one][new issue]!

### Fork & create a branch

If this is something you think you can fix, then [fork the Prohelion software][] and
create a branch with a descriptive name.

A good branch name would be (where issue #325 is the ticket you're working on):

```sh
git checkout -b 325-add-japanese-translations
```

### Get the test suite running

Not all of our software has test cases or test coverage (feel free to contribute!), but where there 
are test cases please make sure you have a 100% pass rates before trying to contribute code back to us.

### Did you find a bug?

* **Ensure the bug was not already reported** by [searching all issues][].

* If you're unable to find an open issue addressing the problem,
  [open a new one][new issue]. Be sure to include a **title and clear
  description**, as much relevant information as possible, and a **code sample**
  or an **executable test case** demonstrating the expected behavior that is not
  occurring.

* If possible, use the relevant bug report templates to create the issue.
  Simply copy the content of the appropriate template into a .rb file, make the
  necessary changes to demonstrate the issue, and **paste the content into the
  issue description**:
  * [**Prohelion** master issues][master template]

### 5. Implement your fix or feature

At this point, you're ready to make your changes! Feel free to ask for help;
everyone is a beginner at first :smile_cat:

### 6. View your changes in across multiple broswers or tablets

One of the biggest challenges we have with our code is making sure that it works across a range of
tablets / PCs / phones and the various technologies that people bring in to race environments.

Please try and test as much as possible across multiple platforms.

### Get the style right

Your patch should follow the same conventions & pass the same code quality
checks as the rest of the project. 

### Make a Pull Request

At this point, you should switch back to your master branch and make sure it's
up to date with the Prohelion ArrowPoint-Tablet master branch:

```sh
git remote add upstream git@github.com:Prohelion/ArrowPoint-Telemetry.git
git checkout master
git pull upstream master
```

Then update your feature branch from your local copy of master, and push it!

```sh
git checkout 325-add-japanese-translations
git rebase master
git push --set-upstream origin 325-add-japanese-translations
```

Finally, go to GitHub and [make a Pull Request][] :D

### Keeping your Pull Request updated

If a maintainer asks you to "rebase" your PR, they're saying that a lot of code
has changed, and that you need to update your branch so it's easier to merge.

To learn more about rebasing in Git, there are a lot of [good][git rebasing]
[resources][interactive rebase] but here's the suggested workflow:

```sh
git checkout 325-add-japanese-translations
git pull --rebase upstream master
git push --force-with-lease 325-add-japanese-translations
```

### Merging a Pull Request (maintainers only)

A Pull Request can only be merged into master by a maintainer if:

* It is passing all test cases
* It has been approved by at least two maintainers. If it was a maintainer who
  opened the Pull Request, only one extra approval is needed.
* It has no requested changes.
* It is up to date with current master.

Any maintainer is allowed to merge a Pull Request if all of these conditions are
met.

### Shipping a release (maintainers only)

Maintainers need to do the following to push out a release:

* Make sure all pull requests are in and that changelog is current
* Update `version.rb` file and changelog with new version number
* If it's not a patch level release, create a stable branch for that release,
  otherwise switch to the stable branch corresponding to the patch release you
  want to ship:

  ```sh
  git checkout master
  git fetch ArrowPoint-Telemetry
  git rebase ArrowPoint-Telemetry/master
  # If the release is 2.1.x then this should be: 2-1-stable
  git checkout -b N-N-stable
  git push ArrowPoint-Tablet N-N-stable:N-N-stable
  ```
[search the issue tracker]: https://github.com/Prohelion/ArrowPoint-Telemetry/issues?q=something
[new issue]: https://github.com/Prohelion/ArrowPoint-Telemetry/issues/new
[fork the Prohelion software]: https://help.github.com/articles/fork-a-repo
[searching all issues]: https://github.com/Prohelion/ArrowPoint-Telemetry/issues?q=
[master template]: https://github.com/Prohelion/ArrowPoint-Telemetry/blob/master/tasks/bug_report_template.rb
[make a pull request]: https://help.github.com/articles/creating-a-pull-request
[git rebasing]: http://git-scm.com/book/en/Git-Branching-Rebasing
[interactive rebase]: https://help.github.com/articles/interactive-rebase
